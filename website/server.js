
const axios = require('axios');
const express = require('express');
const multer = require('multer');
const { exec } = require('child_process');
const fs = require('fs');
const path = require('path');
const cors = require('cors'); 
const mysql = require('mysql2/promise'); 

const app = express();


const dbConfig = {
  host: 'localhost', 
  user: 'root',
  password: 'root',
  database: 'imagesdb',
};


app.use(cors());


const upload = multer({ dest: 'uploads/' });

app.post('/upload', upload.single('imageFile'), (req, res) => {
  const imageFilePath = req.file.path;
  const originalName = req.file.originalname;
  const zoomFactor = req.body.zoomFactor;

 
  const containerPath = `/c01/${originalName}`;

  
  const dockerCpCommand = `docker cp ${imageFilePath} c01-container:${containerPath}`;
  exec(dockerCpCommand, (err, stdout, stderr) => {
    if (err) {
      console.error('Error copying file to container:', stderr);
      fs.unlinkSync(imageFilePath);
      return res.status(500).send('Failed to copy image to container.');
    }

    
    const curlCommand = `docker exec c01-container curl -X POST -F "imageFile=@${containerPath}" -F "zoomFactor=${zoomFactor}" http://localhost:8080/upload`;
    exec(curlCommand, (curlErr, curlStdout, curlStderr) => {
      
      fs.unlinkSync(imageFilePath);

      if (curlErr) {
        console.error('Error executing curl:', curlStderr);
        return res.status(500).send('Failed to process image in container.');
      }

      res.send(`Image processed successfully:\n${curlStdout}`);
    });
  });
});

app.get('/download-latest', async (req, res) => {
  try {
    console.log("Request received for latest image.");
    const connection = await mysql.createConnection(dbConfig);

    
    const [rows] = await connection.execute('SELECT MAX(id) as lastId FROM ProcessedImages');
    const lastId = rows[0].lastId;

    if (!lastId) {
      console.error('No images found in the database.');
      return res.status(404).send('Nu există imagini în baza de date.');
    }
    console.log(`Latest image ID: ${lastId}`);

    
    const timestamp = Date.now();
    const filePath = `/var/lib/mysql-files/image_${timestamp}.bmp`;

    const dumpQuery = `SELECT image FROM ProcessedImages WHERE id = ${lastId} INTO DUMPFILE '${filePath}'`;

    
    await connection.execute(dumpQuery);
    await connection.end();

    console.log(`Image dumped to file: ${filePath}`);

    
    const localFilePath = `./image_${timestamp}.bmp`;
    const dockerCpCommand = `docker cp mysql-container:${filePath} ${localFilePath}`;
    exec(dockerCpCommand, (err, stdout, stderr) => {
      if (err) {
        console.error('Error copying file from container:', stderr);
        return res.status(500).send('Eroare la copierea imaginii din container.');
      }

      console.log(`Image copied to host: ${localFilePath}`);

      
      res.setHeader('Content-Type', 'image/bmp');
      res.setHeader('Content-Disposition', `attachment; filename="image_${timestamp}.bmp"`);
      res.sendFile(path.resolve(localFilePath), (sendErr) => {
        if (sendErr) {
          console.error('Eroare la trimiterea fișierului:', sendErr);
        } else {
          console.log(`File sent successfully: ${localFilePath}`);
          fs.unlinkSync(localFilePath); 
        }
      });
    });
  } catch (err) {
    console.error('Eroare la descărcarea imaginii:', err);
    res.status(500).send('Eroare la descărcarea imaginii.');
  }
});

app.listen(5000, () => {
  console.log('Server running on http://localhost:5000');
});

const express = require('express');
const mysql = require('mysql2/promise');
const bodyParser = require('body-parser');

const app = express();


app.use(bodyParser.raw({ type: 'application/octet-stream', limit: '50mb' }));


let pool;


async function initDatabase() {

  pool = await mysql.createPool({
    host: process.env.MYSQL_HOST || 'localhost',
    user: process.env.MYSQL_USER || 'root',
    password: process.env.MYSQL_PASSWORD || 'root',
    database: process.env.MYSQL_DATABASE || 'imagesdb',
  });

  
  await pool.query(`
    CREATE TABLE IF NOT EXISTS ProcessedImages (
      id INT AUTO_INCREMENT PRIMARY KEY,
      image LONGBLOB NOT NULL,
      createdAt DATETIME DEFAULT CURRENT_TIMESTAMP
    )
  `);
}


app.post('/images', async (req, res) => {
  const imageBuffer = req.body;

 
  if (!imageBuffer || imageBuffer.length === 0) {
    return res.status(400).json({ error: "Imaginea trimisă este goală sau invalidă." });
  }

  try {
    console.log("Received image with size: " + imageBuffer.length);

   
    let [result] = await pool.query('INSERT INTO ProcessedImages (image) VALUES(?)', [imageBuffer]);
    console.log("Image inserted into DB with ID: " + result.insertId);

    return res.status(200).json({
      id: result.insertId
    });
  } catch (err) {
    console.error("Eroare la stocarea imaginii:", err);
    return res.status(500).json({ error: "Eroare internă la stocarea imaginii." });
  }
});


app.get('/images/:id', async (req, res) => {
  const id = req.params.id;

  try {
    let [rows] = await pool.query('SELECT image FROM ProcessedImages WHERE id=?', [id]);
    if (rows.length === 0) {
      console.error(`Imaginea cu ID ${id} nu a fost găsită.`);
      return res.status(404).send('Image not found');
    }

    const imageBuffer = rows[0].image;
    console.log(`Retrieved image with size: ${imageBuffer.length} bytes`);

    res.set('Content-Type', 'image/bmp');
    return res.send(imageBuffer);
  } catch (err) {
    console.error(`Eroare la extragerea imaginii cu ID ${id}:`, err);
    return res.status(500).send('Eroare la extragerea imaginii.');
  }
});


const PORT = 3000;
app.listen(PORT, async () => {
  console.log(`Server started on port ${PORT}`);
  await initDatabase();
});
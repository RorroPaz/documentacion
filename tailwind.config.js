/*
    Comando para iniciar el tailwind
    npx tailwindcss -i ./src/main/resources/static/css/input.css -o ./src/main/resources/static/css/output.css --watch
*/

/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [ // Le dice a Tailwind qué archivos debe analizar para encontrar clases CSS usadas.
    "./src/main/resources/templates/**/*.html", // Para Thymeleaf
    "./src/main/resources/static/**/*.js"       // para JavaScript
  ],
  theme: { // Personaliza colores , fuentes ,etc
    extend: {},
  },
  plugins: [],
}


/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,jsx}", 
  ],
  theme: {
    extend: {
      fontFamily: {
        display: ['Bricolage Grotesque', 'serif'],
        body: ['Inter', 'serif'],
      },
    },
  },
  plugins: [],
}


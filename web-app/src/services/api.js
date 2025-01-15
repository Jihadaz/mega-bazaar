import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:8080/api', // Use the gateway's port
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
  withCredentials: true, // Include credentials for cross-origin requests
});

export default instance;

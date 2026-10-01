import axios from 'axios';

// Android emulator -> host machine. For a physical phone set EXPO_PUBLIC_API_URL to your PC's LAN IP.
const baseURL = process.env.EXPO_PUBLIC_API_URL || 'http://10.0.2.2:8080/api';
const api = axios.create({ baseURL, timeout: 10000, headers: { 'Content-Type': 'application/json' } });
export default api;

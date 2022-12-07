import axios from 'axios'
const baseURL='http://localhost:8088/'

 let instance = axios.create({
	// baseURL: baseURL,
	timeout: 2500
}
);
instance.interceptors.request.use((config) => { console.log(config); return config; });
instance.interceptors.response.use((response) => response.data);
export default instance;
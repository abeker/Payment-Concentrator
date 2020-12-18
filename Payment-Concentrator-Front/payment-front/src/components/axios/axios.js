import axios from 'axios'

export default (history=null) => {
    const baseURL = process.env.REACT_APP_BACKEND_URL
    console.log(baseURL)
    let headers = {}
    if(localStorage.token){
        headers.Authorization = `Bearer ${localStorage.token}`;
    }

    const axiosInstance = axios.create({
        baseURL: baseURL,
        headers
    })

    return axiosInstance;
}
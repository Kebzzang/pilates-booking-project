import axios from 'axios';

const DataFetcher = (url: string) => axios.get(url, { withCredentials: true }).then((response) => response.data);

export default DataFetcher;

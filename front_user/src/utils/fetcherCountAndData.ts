import axios from 'axios';
import moment from 'moment';

const fetcherCountAndData = (selectedDate: moment.Moment, endDate: moment.Moment, url: string) => {
  axios
    .get(url, {
      params: {
        start: selectedDate.format('YYYY-MM-DDT00:00:00'),
        end: endDate.format('YYYY-MM-DDT00:00:00'),
      },
      withCredentials: true,
    })
    .then((response) => {
      return response.data;
    });
};

export default fetcherCountAndData;

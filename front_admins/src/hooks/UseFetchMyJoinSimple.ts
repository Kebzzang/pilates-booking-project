import axios from 'axios';
import { useEffect, useState } from 'react';
import { IClass } from '../types/db';
import moment from 'moment';

export default function useFetchMyJoinSimple(joinPost: boolean, selectedDate: moment.Moment, userId: number) {
  const [data, setData] = useState<number[]>([]);
  useEffect(() => {
    axios
      .get(`http://localhost:8000/api/v1/course/me/${userId}`, {
        withCredentials: true,
      })
      .then((r) => {
        if (r.status !== 204) {
          return r.data;
        } else {
          return { count: 0, data: [] };
        }
      })
      .then((element) => {
        if (element.count !== 0) {
          element.data.map((el: IClass) => {
            setData((prevState) => [...prevState, el.id]);
          });
        }
      });
  }, [selectedDate, joinPost]);

  return data;
}

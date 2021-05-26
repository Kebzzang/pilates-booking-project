import { useEffect, useState } from 'react';
import { IClasses } from '../types/db';
import axios from 'axios';

export default function useFetchMyInfo(userId: number, cancel: boolean) {
  const [data, setData] = useState<IClasses>({ count: 0, data: [] });
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/v1/course/me/${userId}`, {
        withCredentials: true,
      })
      .then((r) => {
        setData(r.data);
      });
  }, [cancel]);

  return data;
}

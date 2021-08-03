import { useEffect, useState } from 'react';
import { IClasses } from '../types/db';
import axios from 'axios';

export default function useFetchMyInfo(userId: number, cancel: boolean) {
  const [data, setData] = useState<IClasses>({ count: 0, data: [] });
  useEffect(() => {
    axios
      .get(`http://3.38.35.210:8080/api/v1/course/me/${userId}`, {
        withCredentials: true,
      })
      .then((r) => {
        if (r.status !== 204) {
          setData(r.data);
        } else {
          setData({
            count: 0,
            data: [],
          });
          console.log('신청한 수업이 없어서 204뜸');
        }
      });
  }, [cancel, userId]);
  return data;
}

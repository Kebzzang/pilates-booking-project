import { useEffect, useState } from 'react';
import { IClass } from '../types/db';
import axios from 'axios';

export default function useFetchMyClasses(userId: number, cancel: boolean) {
  const [data, setData] = useState<IClass[]>([]);
  useEffect(() => {
    axios
      .get(`http://localhost:8000/api/v1/course/me/${userId}`, {
        withCredentials: true,
      })
      .then((r) => {
        if (r.status !== 204) {
          console.log('수업 있음');
          let sorted = [...r.data.data].sort((a, b) => a.courseDateTime.localeCompare(b.courseDateTime));
          setData(sorted);
        } else {
          setData([]);
          console.log('신청한 수업이 없어서 204뜸');
        }
      });
  }, [cancel, userId]);
  return data;
}

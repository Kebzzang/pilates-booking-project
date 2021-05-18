import React, { useCallback, useEffect, useState } from 'react';
import { IClasses } from '../types/db';
import axios from 'axios';

const useClassesSave = (startDate: string, endDate: string) => {
  const [value, setValue] = useState<IClasses>({
    count: 0,
    data: [],
  });
  const save = useCallback((r: IClasses) => {
    setValue(r);
  }, []);

  useEffect(() => {
    axios
      .get('http://localhost:8080/api/v1/course/search', {
        params: {
          start: startDate,
          end: endDate,
        },
      })
      .then((r) => {
        if (r.status !== 204) {
          save(r.data);
          console.log('here' + r.data);
        } else {
          console.log('수업없어서 204뜸');
        }
      });
  }, []);
  return value;
};
export default useClassesSave;

import React, { useCallback, useEffect, useState } from 'react';
import { IClasses } from '../types/db';
import axios from 'axios';
import moment from 'moment';

function useClassFetch(startDate: moment.Moment, joinPost: boolean) {
  const [value, setValue] = useState<IClasses>({
    count: 0,
    data: [],
  });
  const endDate = startDate.clone().add(1, 'days');
  useEffect(() => {
    axios
      .get('http://ec2-3-38-35-210.ap-northeast-2.compute.amazonaws.com:8080/api/v1/course/search', {
        params: {
          start: startDate.format('YYYY-MM-DDT00:00:00'),
          end: endDate.format('YYYY-MM-DDT00:00:00'),
        },
        withCredentials: true,
      })
      .then((r) => {
        if (r.status !== 204) {
          setValue(r.data);
        } else {
          setValue({
            count: 0,
            data: [],
          });
          console.log('수업없어서 204뜸');
        }
      });
  }, [startDate, joinPost]);

  return value;
}
export default useClassFetch;

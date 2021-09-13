import React, { useCallback, useEffect, useState } from 'react';
import moment from 'moment';
import { CalendarWrapper } from './style';
import axios from 'axios';
import { IClasses } from '../../types/db';
import ClassComponent from './Class/ClassComponent';
import useSWR from 'swr';
import Loading from '../../layouts/Loading';
import UseFetchMyJoinSimple from '../../hooks/UseFetchMyJoinSimple';
import './style.css';
import DatePicker from './DatePicker';
import useClassFetch from '../../hooks/useClassFetch';
import fetcherCountAndData from '../../utils/fetcherCountAndData';
import fetcher from '../../utils/fetcher';
const Calendar = () => {
  // const fetcher2 = async (url: string, selectedDate: moment.Moment) => {
  //   const endDate = selectedDate.clone().add(1, 'days');
  //   console.log('endDate', endDate);
  //   const res = await axios.get(url, {
  //     params: {
  //       start: selectedDate.format('YYYY-MM-DDT00:00:00'),
  //       end: endDate.format('YYYY-MM-DDT00:00:00'),
  //     },
  //     withCredentials: true,
  //   });
  //   console.log(res.data);
  //   return res.data;
  // };

  const { data: userData } = useSWR('http://localhost:8000/api/v1/user/me', fetcher, {
    dedupingInterval: 2000,
  }); //내가 원할 때 요청하기!!
  const [selectedDate, setSelectedDate] = useState(moment());

  const joinRequest = (course_id: number, user_id: number) => {
    axios
      .post('http://localhost:8000/api/v1/user/course/join', { course_id, user_id }, { withCredentials: true })
      .then((response) => {
        console.log('성공');
      })
      .catch((error) => {
        alert('problem');
      });
  };

  return (
    <CalendarWrapper>
      <DatePicker onChange={setSelectedDate} value={selectedDate} />
      <ClassComponent userData={userData.id} selectedDate={selectedDate} />
    </CalendarWrapper>
  );
};

export default Calendar;

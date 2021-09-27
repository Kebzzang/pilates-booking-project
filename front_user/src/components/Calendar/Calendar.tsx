import React, { useCallback, useEffect, useState } from 'react';
import moment from 'moment';
import { CalendarWrapper } from './style';
import axios from 'axios';
import ClassComponent from './Class/ClassComponent';
import useSWR from 'swr';
import './style.css';
import DatePicker from './DatePicker';
import fetcher from '../../utils/fetcher';
const Calendar = () => {
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

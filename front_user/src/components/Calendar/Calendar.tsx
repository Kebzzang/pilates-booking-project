import React, { useCallback, useEffect, useState } from 'react';
import moment from 'moment';
import { CalendarWrapper } from './style';
import axios from 'axios';
import { IClasses } from '../../types/db';
import ClassComponent from './Class/ClassComponent';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
import Loading from '../../layouts/Loading';
import UseFetchMyJoinSimple from '../../hooks/UseFetchMyJoinSimple';
import './style.css';
import DatePicker from './DatePicker';
import useClassFetch from '../../hooks/useClassFetch';
const Calendar = () => {
  const { data: userData } = useSWR(
    'http://ec2-3-38-35-210.ap-northeast-2.compute.amazonaws.com:8080/api/v1/user/me',
    fetcher,
    {
      dedupingInterval: 2000,
    },
  ); //내가 원할 때 요청하기!!
  const [selectedDate, setSelectedDate] = useState(moment());
  const [joinPost, setJoinPost] = useState(false);

  const myJoins = UseFetchMyJoinSimple(joinPost, selectedDate, userData.id);
  const classes = useClassFetch(selectedDate, joinPost);

  const joinRequest = (course_id: number, user_id: number) => {
    axios
      .post(
        'http://ec2-3-38-35-210.ap-northeast-2.compute.amazonaws.com:8080/api/v1/user/course/join',
        { course_id, user_id },
        { withCredentials: true },
      )
      .then((response) => {
        console.log('성공: ');
        setJoinPost(!joinPost);
      })
      .catch((error) => {
        alert('problem');
      });
  };

  return (
    <CalendarWrapper>
      <DatePicker onChange={setSelectedDate} value={selectedDate} />
      <ClassComponent props={classes} myJoins={myJoins} joinRequest={joinRequest} />
    </CalendarWrapper>
  );
};

export default Calendar;
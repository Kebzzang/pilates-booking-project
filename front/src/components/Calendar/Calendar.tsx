import React, { useEffect, useState } from 'react';
import moment from 'moment';
import { CalendarWrapper } from './style';
import axios from 'axios';
import { IClasses } from '../../types/db';
import ClassComponent from './ClassComponent';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
import Loading from '../../layouts/Loading';

import './style.css';
import DatePicker from './DatePicker';
const Calendar = () => {
  const { data: userData } = useSWR('http://localhost:8080/api/v1/user/me', fetcher, {
    dedupingInterval: 2000,
  }); //내가 원할 때 요청하기!!
  const [selectedDate, setSelectedDate] = useState(moment());
  const [myJoins, setMyJoins] = useState<number[]>([]);
  const [joinPost, setJoinPost] = useState(false);

  const [loading, setLoading] = useState(true);

  const [classes, setClasses] = useState<IClasses>({
    count: 0,
    data: [],
  });

  const fetchMyJoin = () => {
    axios
      .get(`http://localhost:8080/api/v1/course/me/${userData.id}`, {
        withCredentials: true,
      })
      .then((r) => {
        setMyJoins([]);
        if (r.status !== 204) {
          return r.data;
        } else {
          return { count: 0, data: [] };
        }
      })
      .then((data: IClasses) => {
        if (data.count !== 0) {
          data.data.map((element) => {
            setMyJoins((prevState) => [...prevState, element.id]);
          });
        }
      });
  };
  const joinRequest = (course_id: number, user_id: number) => {
    axios
      .post('http://localhost:8080/api/v1/user/course/join', { course_id, user_id }, { withCredentials: true })
      .then((response) => {
        console.log('성공: ');
        fetchMyJoin();
        setJoinPost(!joinPost);
      })
      .catch((error) => {
        alert('problem');
      });
  };

  useEffect(() => {
    fetchMyJoin();
  }, [selectedDate]);

  useEffect(() => {
    const endDate = selectedDate.clone().add(1, 'days');
    axios
      .get('http://localhost:8080/api/v1/course/search', {
        withCredentials: true,
        params: {
          start: selectedDate.format('YYYY-MM-DDT00:00:00'),
          end: endDate.format('YYYY-MM-DDT00:00:00'),
        },
      })
      .then((r) => {
        if (r.status !== 204) {
          setClasses(r.data);
        } else {
          setClasses({
            count: 0,
            data: [],
          });
          console.log('수업없어서 204뜸');
        }
        setLoading(false);
      });
  }, [selectedDate, joinPost]);

  if (loading) {
    return <Loading />;
  }
  return (
    <CalendarWrapper>
      <DatePicker onChange={setSelectedDate} value={selectedDate} />
      <ClassComponent props={classes} myJoins={myJoins} joinRequest={joinRequest} />
    </CalendarWrapper>
  );
};

export default Calendar;

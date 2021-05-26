import React, { useCallback, useState } from 'react';

import { VerticalTimeline } from 'react-vertical-timeline-component';

import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
import useFetchMyInfo from '../../hooks/useFetchMyInfo';
import moment from 'moment';
import axios from 'axios';
import MyClassElement from './MyClassElement';
import { Form } from 'react-bootstrap';
//정렬 함수 만들어서 두 정렬? 아니면 선생님 별로 저열ㄹ 한 것들 모아서 모음 함수를 만들어주고 ...ㅠㅠ
const MyClass = () => {
  const { data: userData } = useSWR('http://localhost:8080/api/v1/user/me', fetcher, {
    dedupingInterval: 40000,
  });
  const options = ['ascending', 'descending'];
  const [cancel, setCancel] = useState(false);
  const myJoins = useFetchMyInfo(userData.id, cancel);
  const now = moment();

  const [myOption, setMyOption] = useState(options[0]);
  const handleOptionChange = (event: any) => {
    console.log(event.target.value);
    setMyOption(event.target.value);
  };

  const cancelCheck = useCallback(
    (day: moment.Moment) => {
      return day.subtract(1, 'day').isAfter(now);
    },
    [now],
  );

  const cancelJoin = (course_id: number) => {
    axios
      .delete('http://localhost:8080/api/v1/user/course/cancel', {
        withCredentials: true,
        params: { course_id: course_id, user_id: userData.id },
      })
      .then((response) => {
        console.log('성공');
        setCancel(!cancel);
      })
      .catch((error) => {
        alert('problem');
      });
  };

  return (
    <div style={{ width: '441px', marginLeft: 'auto', marginRight: 'auto' }}>
      <select style={{ width: '100%' }} onChange={handleOptionChange}>
        {options.map((element) => (
          <option value={element}>{element}</option>
        ))}
      </select>
      <VerticalTimeline layout={'1-column'}>
        {myJoins.data
          .sort((a, b) => b.courseDateTime.localeCompare(a.courseDateTime))
          .map((element) => (
            <MyClassElement cancelCheck={cancelCheck} element={element} cancelJoin={cancelJoin} />
          ))}
      </VerticalTimeline>
    </div>
  );
};

export default MyClass;

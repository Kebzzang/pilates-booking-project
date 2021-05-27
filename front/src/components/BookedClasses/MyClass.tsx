import React, { useCallback, useEffect, useState } from 'react';

import { VerticalTimeline } from 'react-vertical-timeline-component';

import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
import useFetchMyInfo from '../../hooks/useFetchMyInfo';
import moment from 'moment';
import axios from 'axios';
import MyClassElement from './MyClassElement';
import Select from 'react-select';
import { IClass } from '../../types/db';
//정렬 함수 만들어서 두 정렬? 아니면 선생님 별로 저열ㄹ 한 것들 모아서 모음 함수를 만들어주고 ...ㅠㅠ
const MyClass = () => {
  const { data: userData } = useSWR('http://localhost:8080/api/v1/user/me', fetcher, {
    dedupingInterval: 40000,
  });
  const options = [
    { value: 'ascending', label: 'Ascending' },
    { value: 'descending', label: 'Descending' },
  ];

  const [cancel, setCancel] = useState(false);

  const myJoins = useFetchMyInfo(userData.id, cancel);
  const now = moment();
  const [sortedMyJoins, setSortedMyJoins] = useState<IClass[]>(
    [...myJoins.data].sort((a, b) => a.courseDateTime.localeCompare(b.courseDateTime)),
  );
  const [myOption, setMyOption] = useState('ascending');

  useEffect(() => {
    console.log(1, myOption);

    const sort = (option: string) => {
      if (option === 'ascending') {
        const sorted = [...myJoins.data].sort((a, b) => a.courseDateTime.localeCompare(b.courseDateTime));
        setSortedMyJoins(sorted);
      }
      if (option === 'descending') {
        const sorted = [...myJoins.data].sort((a, b) => b.courseDateTime.localeCompare(a.courseDateTime));
        setSortedMyJoins(sorted);
      }
    };
    sort(myOption);
  }, [myOption, sortedMyJoins]);

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
    <div style={{ width: '400px', marginLeft: 'auto', marginTop: '20px', marginRight: 'auto' }}>
      <select style={{ width: '100%' }} onChange={(e) => setMyOption(e.target.value)}>
        <option defaultValue="ascending" value="ascending">
          Ascending
        </option>
        <option value="descending">Descending</option>
      </select>
      <VerticalTimeline layout={'1-column'}>
        {sortedMyJoins.map((element) => (
          <MyClassElement key={element.id} cancelCheck={cancelCheck} element={element} cancelJoin={cancelJoin} />
        ))}
      </VerticalTimeline>
    </div>
  );
};

export default MyClass;

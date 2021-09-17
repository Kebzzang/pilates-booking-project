import React, { useCallback, useEffect, useState } from 'react';

import { VerticalTimeline } from 'react-vertical-timeline-component';
import useInput from '../../hooks/useInput';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
import moment from 'moment';
import axios from 'axios';
import MyClassElement from './MyClassElement';
import { IClass } from '../../types/db';
import useFetchMyClasses from '../../hooks/useFetchMyClasses';
//정렬 함수 만들어서 두 정렬? 아니면 선생님 별로 저열ㄹ 한 것들 모아서 모음 함수를 만들어주고 ...ㅠㅠ
const MyClass = () => {
  const { data: userData } = useSWR('http://localhost:8000/api/v1/user/me', fetcher, {
    dedupingInterval: 40000,
  });
  const [cancel, setCancel] = useState(false);

  const myJoins = useFetchMyClasses(userData.id, cancel);

  const now = moment();
  const [sortedMyJoins, setSortedMyJoins] = useState<IClass[]>([]);
  const [myOption, setMyOption] = useState('ascending');
  const [mySearch, onChangeMySearch] = useInput('');

  useEffect(() => {
    if (myOption === 'ascending') {
      const _sortedMyJoins = [...myJoins].sort((a, b) => a.courseDateTime.localeCompare(b.courseDateTime));
      setSortedMyJoins(_sortedMyJoins);
    }
    if (myOption === 'descending') {
      const _sortedMyJoins = [...myJoins].sort((a, b) => b.courseDateTime.localeCompare(a.courseDateTime));
      setSortedMyJoins(_sortedMyJoins);
    }
  }, [myOption, myJoins]);

  const cancelCheck = useCallback(
    (day: moment.Moment) => {
      return day.subtract(1, 'day').isAfter(now);
    },
    [now],
  );
  function search(IClassRows: IClass[]) {
    let lowerMySearch = mySearch.toLowerCase();
    return IClassRows.filter(
      (IClassRow) =>
        IClassRow.teacher_name.toLowerCase().indexOf(lowerMySearch) > -1 ||
        IClassRow.title.toLowerCase().indexOf(lowerMySearch) > -1 ||
        IClassRow.equipmentType.toLowerCase().indexOf(lowerMySearch) > -1,
    );
  }
  const cancelJoin = (course_id: number) => {
    axios
      .delete('http://localhost:8000/api/v1/user/course/cancel', {
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
      <input
        type="text"
        value={mySearch}
        onChange={onChangeMySearch}
        placeholder="search"
        style={{ width: '70%', height: '25px', marginRight: '10px' }}
      />
      <select defaultValue="ascending" onChange={(e) => setMyOption(e.target.value)}>
        <option value="ascending">Ascending</option>
        <option value="descending">Descending</option>
      </select>

      <VerticalTimeline layout={'1-column'}>
        {search(sortedMyJoins).map((element) => (
          <MyClassElement key={element.id} cancelCheck={cancelCheck} element={element} cancelJoin={cancelJoin} />
        ))}
      </VerticalTimeline>
    </div>
  );
};

export default MyClass;

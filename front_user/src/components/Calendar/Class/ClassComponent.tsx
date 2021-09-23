import React, { FC, useEffect, useState } from 'react';
import { IClass, IClasses, ICourseClass, ICourseClasses, ISimpleUser } from '../../../types/db';
import Button from 'react-bootstrap/Button';
import { VerticalTimeline, VerticalTimelineElement } from 'react-vertical-timeline-component';
import 'react-vertical-timeline-component/style.min.css';
import './ClassComponent.css';
import { NoClass } from '../style';
import useSWR from 'swr';
import fetcher from '../../../utils/fetcher';
import moment from 'moment';
import { HiOutlineUserCircle } from 'react-icons/hi';
import { GrClock } from 'react-icons/all';
import axios from 'axios';
import fetcherCountAndData from '../../../utils/fetcherCountAndData';
import Loading from '../../../layouts/Loading';
interface ClassInfoProps {
  userData: number;
  selectedDate: moment.Moment;
}
const ClassComponent: FC<ClassInfoProps> = ({ userData, selectedDate }) => {
  const today = moment();
  const joinStyles = { background: '#05495e', width: '13px', height: '13px' };
  const greyStyles = { background: 'lightgray', height: '13px', width: '13px' };
  const endDate = selectedDate.clone().add(1, 'days');

  const classFetcher = async (url: string) => {
    console.log('endDate', endDate);
    const res = await axios.get(url, {
      withCredentials: true,
    });
    return res.data;
  };
  const { data: classes, error, mutate } = useSWR(
    'http://localhost:8000/api/v1/course/search?start=' +
      selectedDate.format('YYYY-MM-DDT00:00:00') +
      '&end=' +
      endDate.format('YYYY-MM-DDT00:00:00'),
    classFetcher,
  );

  if (error) return <div>failed to load</div>;
  if (!classes) return <Loading />;

  const onClickJoins = (course_id: number, user_id: number) => {
    axios
      .post('http://localhost:8000/api/v1/user/course/join', { course_id, user_id }, { withCredentials: true })
      .then(() => mutate());
  };
  //해당 회원이 해당 수업들에 포함되었는지 안포함되었는지 체크하기
  function checkUserJoin(data: ISimpleUser[]) {
    let result;
    data.map((user) => {
      user.id === userData ? (result = true) : (result = false);
    });

    return result;
  }
  return (
    <div style={{ width: '441px', marginLeft: 'auto', marginRight: 'auto' }}>
      {classes.count !== 0 ? (
        <div>
          <VerticalTimeline layout={'1-column'}>
            {classes.data
              .filter((element: ICourseClass) => today.isSameOrBefore(moment(element.courseDateTime)))
              .sort((a: ICourseClass, b: ICourseClass) => a.courseDateTime.localeCompare(b.courseDateTime))
              .map((element: ICourseClass) =>
                element ? (
                  <VerticalTimelineElement
                    contentArrowStyle={{ borderRight: 'transparent' }}
                    key={element.id}
                    iconStyle={checkUserJoin(element.users) ? joinStyles : greyStyles}
                  >
                    <div className="date">
                      {element.equipmentType}{' '}
                      <div style={{ float: 'right' }}>
                        <GrClock />
                        &nbsp;{element.courseDateTime.slice(11, 16)}
                      </div>
                    </div>

                    <hr style={{ marginTop: '10px', marginBottom: '10px', marginLeft: '0px', marginRight: '0px' }} />
                    <div className="description">
                      <HiOutlineUserCircle size="30" />
                      &nbsp;&nbsp;
                      {element.teacher_name}
                      <br />
                      {element.title}
                      <div style={{ fontWeight: 'bolder', marginBottom: '5px', float: 'right' }}>
                        {element.nowStudent}/{element.maxStudent}
                      </div>
                    </div>
                    {checkUserJoin(element.users) ? (
                      <Button variant="info" disabled block style={{ float: 'right' }}>
                        Joined
                      </Button>
                    ) : //수업신청안했다면
                    element.nowStudent === element.maxStudent ? (
                      <Button variant="light" disabled block style={{ float: 'right' }}>
                        Full
                      </Button>
                    ) : (
                      <Button
                        target={element.id}
                        block
                        style={{ float: 'right' }}
                        variant="outline-info"
                        onClick={() => {
                          onClickJoins(element.id, userData);
                          mutate();
                        }}
                      >
                        Join
                      </Button>
                    )}
                  </VerticalTimelineElement>
                ) : (
                  <NoClass>No Class</NoClass>
                ),
              )}
          </VerticalTimeline>
        </div>
      ) : (
        <NoClass> No Class</NoClass>
      )}
    </div>
  );
};

export default ClassComponent;

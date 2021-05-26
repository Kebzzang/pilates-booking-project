import React, { FC, useEffect, useState } from 'react';
import { IClasses } from '../../../types/db';
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
interface ClassInfoProps {
  props: IClasses;
  myJoins: number[];
  joinRequest: Function;
}
const ClassComponent: FC<ClassInfoProps> = ({ joinRequest, myJoins, props }) => {
  const { data: userData, error, revalidate, mutate } = useSWR('http://localhost:8080/api/v1/user/me', fetcher, {
    dedupingInterval: 20000,
  }); //내가 원할 때 요청하기!!
  const today = moment();

  const joinStyles = { background: '#05495e', width: '13px', height: '13px' };
  const greyStyles = { background: 'lightgray', height: '13px', width: '13px' };
  const { count, data } = props;

  const [newJoins, setNewJoins] = useState<number[]>([]);

  useEffect(() => {
    setNewJoins(myJoins);
  }, [myJoins]);

  const onClickJoins = (course_id: number, user_id: number) => {
    joinRequest(course_id, user_id);
  };

  return (
    <div style={{ width: '441px', marginLeft: 'auto', marginRight: 'auto' }}>
      {count !== 0 ? (
        <div>
          <VerticalTimeline layout={'1-column'}>
            {data
              .filter((element) => today.isSameOrBefore(moment(element.courseDateTime)))
              .sort((a, b) => a.courseDateTime.localeCompare(b.courseDateTime))
              .map((element) => (
                <VerticalTimelineElement
                  contentArrowStyle={{ borderRight: 'transparent' }}
                  key={element.id}
                  iconStyle={newJoins.includes(element.id) ? joinStyles : greyStyles}
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
                  {newJoins.includes(element.id) ? (
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
                      onClick={() => onClickJoins(element.id, userData.id)}
                    >
                      Join
                    </Button>
                  )}
                </VerticalTimelineElement>
              ))}
          </VerticalTimeline>
        </div>
      ) : (
        <NoClass> No Class</NoClass>
      )}
    </div>
  );
};

export default ClassComponent;

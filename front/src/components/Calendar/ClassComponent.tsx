import React, { FC, PropsWithChildren, useEffect, useState } from 'react';
import { IClasses } from '../../types/db';
import Button from 'react-bootstrap/Button';
import { VerticalTimeline, VerticalTimelineElement } from 'react-vertical-timeline-component';
import 'react-vertical-timeline-component/style.min.css';
import './ClassComponent.css';
import { HR, NoClass } from './style';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
import moment from 'moment';
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

  const joinStyles = { background: '#05495e', width: '10px', height: '10px' };
  const greyStyles = { background: 'lightgray', height: '10px', width: '10px' };
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
                    {element.courseDateTime.slice(11, 16)} By {element.teacher_name}
                  </div>
                  <hr style={{ marginTop: '10px', marginBottom: '10px', marginLeft: '0px', marginRight: '0px' }} />
                  <div className="description">
                    {element.title}
                    <br />
                    {element.equipmentType}
                    <div style={{ fontWeight: 'bolder', marginBottom: '5px', float: 'right' }}>
                      {element.nowStudent}/{element.maxStudent}
                    </div>
                  </div>
                  {newJoins.includes(element.id) ? (
                    <Button variant="outline-primary" disabled block style={{ float: 'right' }}>
                      Joined
                    </Button>
                  ) : //수업신청안했다면
                  element.nowStudent === element.maxStudent ? (
                    <Button variant="outline-warning" disabled block style={{ float: 'right' }}>
                      Full
                    </Button>
                  ) : (
                    <Button
                      target={element.id}
                      block
                      style={{ float: 'right' }}
                      variant="info"
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

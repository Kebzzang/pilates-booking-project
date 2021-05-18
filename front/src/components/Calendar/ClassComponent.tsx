import React, { FC, PropsWithChildren, useEffect } from 'react';
import { IClass, IClasses } from '../../types/db';
import { Button, Card } from 'react-bootstrap';
import defaultProfile from '../../img/defaultProfile.png';
import './style.css';
import { CardHeaders } from './style';
import { VerticalTimeline, VerticalTimelineElement } from 'react-vertical-timeline-component';
import 'react-vertical-timeline-component/style.min.css';
import axios from 'axios';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
interface ClassInfoProps {
  props: IClasses;
}
const ClassComponent: FC<ClassInfoProps> = ({ props, children }) => {
  const { data: userData, error, revalidate, mutate } = useSWR('http://localhost:8080/api/v1/user/me', fetcher, {
    dedupingInterval: 2000,
  }); //내가 원할 때 요청하기!!
  const { count, data } = props;
  const redStyles = { background: '#05495e', height: '10px', width: '10px' };
  const showJoinButton = () => {};
  //useEffect로 일단 얘가 해당 수업에 있는지 이걸 체크하는 백엔드 서버를 만들어야할듯??
  //수업 아이디 쫙 보내서 join 테이블에 얘 있는지??
  useEffect(() => {
    data.map((element) => {
      axios.get('http://localhost:8080/api/v1/user/joininfo');
    });
  }, []);

  return (
    <div style={{ width: '441px', marginLeft: 'auto', marginRight: 'auto' }}>
      {count !== 0 ? (
        <div>
          <div>
            {children} : {count} {count === 1 ? <>class is</> : <>classes are</>} available.
          </div>

          <VerticalTimeline layout={'1-column'}>
            {data.map((element) => (
              <VerticalTimelineElement key={element.id} date={element.equipmentType} iconStyle={redStyles}>
                <h6 className="vertical-timeline-element-title">{element.title}</h6>
                <h6 className="vertical-timeline-element-subtitle">Ms.{element.teacher_name}</h6>
                <p id="description">{element.maxStudent}</p>
              </VerticalTimelineElement>
            ))}
          </VerticalTimeline>
        </div>
      ) : (
        <div>{children} No Class</div>
      )}
    </div>
  );
};

export default ClassComponent;

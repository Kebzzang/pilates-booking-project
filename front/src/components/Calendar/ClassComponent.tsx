import React, { FC, PropsWithChildren } from 'react';
import { IClass, IClasses } from '../../types/db';
import { Card } from 'react-bootstrap';
//각 카드에 버튼 추가 -> 수업 신청하기 신청하면 신청됏다고 추가 메시지 표시.
interface ClassInfoProps {
  props: IClasses;
}
const ClassComponent: FC<ClassInfoProps> = ({ props, children }) => {
  const { count, data } = props;
  //
  return (
    <div>
      {count !== 0 ? (
        <>
          {children} : {count} classes can be booked.
          {data.map((d) => (
            <Card border="info" style={{ width: '10rem', height: '5rem' }}>
              <Card.Header style={{ height: '1rem', textAlign: 'center' }}>{d.teacher_name}</Card.Header>
              <Card.Body>
                <Card.Subtitle>{d.equipmentType}</Card.Subtitle>
                <footer className="blockquote-footer">{d.courseDateTime.slice(10, 14)}</footer>
              </Card.Body>
            </Card>
          ))}
        </>
      ) : (
        <h2>{children} No Class</h2>
      )}
    </div>
  );
};

export default ClassComponent;

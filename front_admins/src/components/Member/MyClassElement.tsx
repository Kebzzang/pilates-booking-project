import React, { FC } from 'react';
import { VerticalTimelineElement } from 'react-vertical-timeline-component';
import { FaUserCircle } from 'react-icons/all';
import moment from 'moment';
import Button from 'react-bootstrap/Button';
import { IClass } from '../../types/db';

interface ClassElement {
  element: IClass;
  cancelJoin: Function;
  cancelCheck: Function;
}
const MyClassElement: FC<ClassElement> = ({ element, cancelJoin, cancelCheck }) => {
  const joinStyles = { background: '#05495e', width: '10px', height: '10px' };
  return (
    <VerticalTimelineElement contentArrowStyle={{ borderRight: 'transparent' }} key={element.id} iconStyle={joinStyles}>
      <div className="date">
        {element.equipmentType}{' '}
        <div style={{ float: 'right' }}>
          {element.courseDateTime.slice(0, 10) + ' ' + element.courseDateTime.slice(11, 16)}
        </div>
      </div>
      <hr style={{ marginTop: '10px', marginBottom: '10px', marginLeft: '0px', marginRight: '0px' }} />
      <div className="description">
        <FaUserCircle />
        &nbsp;&nbsp;
        {element.teacher_name}
        <br />
        {element.title}
      </div>
      {cancelCheck(moment(element.courseDateTime)) && (
        <Button
          target={element.id}
          block
          style={{ float: 'right' }}
          variant="info"
          onClick={() => cancelJoin(element.id)}
        >
          cancel
        </Button>
      )}
    </VerticalTimelineElement>
  );
};

export default MyClassElement;

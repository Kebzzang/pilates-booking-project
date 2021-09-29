import { IClass } from '../../types/db';

import { useHistory } from 'react-router-dom';
import { Table } from 'react-bootstrap';
import { DetailDiv } from './style/SaveStyle';
import React from 'react';

export function CourseListItem(props: { courseData: IClass }) {
  const courseData = props.courseData;
  const history = useHistory();
  let datetime = courseData.courseDateTime.slice(0, 10) + ' ' + courseData.courseDateTime.slice(11, 16);
  return (
    <tr title={datetime} style={{ cursor: 'pointer' }} onClick={() => history.push(`/lessons/${courseData.id}`)}>
      <td>{courseData.title}</td>
      <td>{courseData.equipmentType}</td>
      <td style={{}}>{courseData.teacher_name}</td>
      <td>
        {courseData.nowStudent}/{courseData.maxStudent}
      </td>
      <td>{datetime}</td>
    </tr>
  );
}

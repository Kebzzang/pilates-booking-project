import { IClass } from '../../types/db';

export function CourseListItem(props: { courseData: IClass }) {
  const courseData = props.courseData;
  return (
    <div>
      <h4>{courseData.title}</h4>
      <h4>{courseData.equipmentType}</h4>
    </div>
  );
}

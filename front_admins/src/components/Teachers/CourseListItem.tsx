import { IClass } from '../../types/db';

export function CourseListItem(props: { courseData: IClass }) {
  const courseData = props.courseData;
  return (
    <div style={{ marginTop: '10px' }}>
      <h6>
        {courseData.title} -{courseData.equipmentType}
      </h6>
      <p>
        {courseData.courseDateTime.slice(0, 10)} {courseData.courseDateTime.slice(11, 16)}
      </p>
    </div>
  );
}

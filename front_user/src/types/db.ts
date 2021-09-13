export interface IUser {
  id: number;
  username: string;
  email: string;
  role: string;
  certified: string;
}
export interface ISimpleUser {
  id: number;
  username: string;
}
export interface ICourseClass {
  id: number;
  title: string;
  content: string;
  equipmentType: string;
  courseDateTime: string;
  date: string;
  teacher_name: string;
  maxStudent: number;
  nowStudent: number;
  users: ISimpleUser[];
}
export interface IClass {
  id: number;
  title: string;
  content: string;
  equipmentType: string;
  courseDateTime: string;
  date: string;
  teacher_name: string;
  maxStudent: number;
  nowStudent: number;
}
export interface ITeacher {
  id: number;
  name: string;
  userProfileImageLink: string;
  email: string;
}
export interface IClasses {
  count: number;
  data: IClass[];
}
export interface ICourseClasses {
  count: number;
  data: ICourseClass[];
}

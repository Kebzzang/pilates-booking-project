export interface IUser {
  id: number;
  username: string;
  email: string;
  role: string;
  certified: string;
}
export interface IClass {
  id: number;
  title: string;
  content: string;
  equipmentType: string;
  courseDateTime: string;
  date: string;
  teacher_name: string;
}

export interface IClasses {
  count: number;
  data: IClass[];
}

import { useEffect, useState } from 'react';
import axios from 'axios';
import { ITeacher } from '../types/db';

export default function useTeacherFetch() {
  const [profiles, setProfiles] = useState<ITeacher[]>([]);
  useEffect(() => {
    axios
      .get('http://localhost:8000/api/v1/admin/teacher', {
        withCredentials: true,
      })
      .then((r) => {
        if (r.status !== 204) {
          setProfiles(r.data.data);
        }
      });
  }, []);
  return profiles;
}

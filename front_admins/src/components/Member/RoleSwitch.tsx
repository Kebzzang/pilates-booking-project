import React, { FC, useCallback } from 'react';
import { Form } from '../Teachers/style/SaveStyle';
import { IUser } from '../../types/db';
import axios from 'axios';
import { useHistory } from 'react-router-dom';
import useInput from '../../hooks/useInput';
import { FcCheckmark, MdClose } from 'react-icons/all';
import { FlexDiv, IconButton, RoleSelect, RoleSelectForm } from './style';
import { mutate } from 'swr';
interface Props {
  element: IUser;
  show: Function;
}
const RoleSwitch: FC<Props> = ({ element, show }) => {
  const [role, onChangeRole] = useInput(element.role);
  const onUpdateSubmit = useCallback(
    (e) => {
      e.preventDefault();
      axios
        .put(`http://localhost:8000/api/v1/admin/user/${element.id}`, { role }, { withCredentials: true })
        .then(() => mutate('http://localhost:8000/api/v1/admin/user'))
        .finally(show());
    },
    [element.id, history, role],
  );

  return (
    <RoleSelectForm onSubmit={onUpdateSubmit}>
      <RoleSelect key={role} defaultValue={role} onChange={onChangeRole}>
        <option value="ROLE_USER">USER</option>
        <option value="ROLE_TEACHER">TEACHER</option>
        <option value="ROLE_ADMIN">ADMIN</option>
      </RoleSelect>
      <div>
        <IconButton type="submit">
          <FcCheckmark />
        </IconButton>
        <IconButton onClick={() => show()}>
          <MdClose color="red" />
        </IconButton>
      </div>
    </RoleSelectForm>
  );
};

export default RoleSwitch;

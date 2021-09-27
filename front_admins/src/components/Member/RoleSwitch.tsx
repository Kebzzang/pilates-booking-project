import React, { FC, useCallback } from 'react';
import { IUser } from '../../types/db';
import axios from 'axios';
import useInput from '../../hooks/useInput';
import { FcCheckmark, MdClose } from 'react-icons/all';
import { IconButton, RoleSelect, RoleSelectForm } from './style';
import { mutate } from 'swr';
interface Props {
  element: IUser;
  show: Function;
}
const RoleSwitch: FC<Props> = ({ element, show }) => {
  const [role, onChangeRole] = useInput(element.role);
  //업데이트 요청 성공시 swr은 다시 최신 정보로 유저들에 대한 정보를 업데이트한다.
  //업데이트 요청이 성공하든 실패하든 showRoles는 false로 변경되어 일반적인 유저 롤 정보를 보여준다.
  const onUpdateSubmit = useCallback(
    (e) => {
      e.preventDefault();
      axios
        .put(`http://localhost:8000/api/v1/admin/user/${element.id}`, { role }, { withCredentials: true })
        .then(() => mutate('http://localhost:8000/api/v1/admin/user'))
        .finally(show());
    },
    [element.id, role, show],
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

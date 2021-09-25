import React, { FC, useCallback, useState } from 'react';
import { RoleBadge, FlexDiv, Clickable } from './style';
import { Link } from 'react-router-dom';
import { GiHamburgerMenu, RiEdit2Fill } from 'react-icons/all';
import { IUser } from '../../types/db';
import RoleSwitch from './RoleSwitch';

interface Props {
  element: IUser;
}
const MemberListItem: FC<Props> = ({ element }) => {
  const [showRoles, setShowRoles] = useState(false);
  const onClickRole = useCallback(
    (e) => {
      console.log(`clicked ${element.id}`);
      setShowRoles((prev) => !prev);
    },
    [element.id],
  );
  return (
    <tr>
      <td>{element.username}</td>
      <td style={{ width: '180px' }}>
        {/*유저 롤은 ROLE_**이므로 슬라이스해서 뱃지의 props로 넘겨줌*/}

        {!showRoles ? (
          <FlexDiv>
            <RoleBadge roleStyle={element.role}>{element.role.slice(5)}</RoleBadge>
            <Clickable onClick={onClickRole}>
              <RiEdit2Fill />
            </Clickable>
          </FlexDiv>
        ) : (
          <RoleSwitch show={setShowRoles} element={element} />
        )}
      </td>
      <td>{element.email}</td>
      <td>
        <Link to={{ pathname: `/members/${element.id}`, state: element }}>
          <GiHamburgerMenu color="gray" />
        </Link>
      </td>
    </tr>
  );
};

export default MemberListItem;

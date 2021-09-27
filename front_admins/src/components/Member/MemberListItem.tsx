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
  //showRoles가 true이면 유저 롤을 변경할 수 있는 Select 창이 나타나게 함.
  const [showRoles, setShowRoles] = useState(false);

  //showRoles를 상태변경하는 onClickRole
  const onClickRole = useCallback((e) => {
    //true는 false로, false는 true로
    setShowRoles((prev) => !prev);
  }, []);

  return (
    <tr>
      <td>{element.username}</td>
      <td style={{ width: '180px' }}>
        {/*유저 롤은 ROLE_**이므로 슬라이스해서 뱃지의 props로 넘겨줌*/}
        {/*만약 showRoles가 트루이면 */}
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
        {element.role === 'ROLE_USER' && (
          <Link to={{ pathname: `/members/${element.id}`, state: element }}>
            <GiHamburgerMenu color="gray" />
          </Link>
        )}
      </td>
    </tr>
  );
};

export default MemberListItem;

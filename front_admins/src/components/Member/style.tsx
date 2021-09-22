import styled from '@emotion/styled';
interface roleStyle {
  roleStyle: string;
  //롤에 따라 뱃지의 스타일을 다르게 줌
}

const handleRoleStyle = (roleStyle: string) => {
  switch (roleStyle) {
    case 'ROLE_ADMIN':
      return '#0da6d9';
    case 'ROLE_TEACHER':
      return '#17b03b';
    case 'ROLE_USER':
      return '#6e6e6e';
  }
};
export const RoleBadge = styled.span<roleStyle>`
  background-color: ${(props) => handleRoleStyle(props.roleStyle)};
  color: white;
  border-radius: 10px;
  font-size: small;
  padding: 3px 8px 3px 8px;
`;

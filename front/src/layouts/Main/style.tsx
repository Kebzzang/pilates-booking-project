import styled from '@emotion/styled';

export const RightMenu = styled.div`
  float: right;
`;
export const LeftMenu = styled.div`
  float: left;
  cursor: pointer;
  font-size: 24px;
  padding-right: 30px;
`;
export const Header = styled.header`
  height: 28px;
  background: #14a0a0;
  color: #ffffff;
  box-shadow: 0 1px 0 0 rgba(255, 255, 255, 0.1);
  padding: 5px;
  text-align: center;
`;

export const ProfileModal = styled.div`
  display: flex;
  padding: 20px;

  & img {
    display: flex;
  }

  & > div {
    display: flex;
    flex-direction: column;
    margin-left: 10px;
  }

  & #profile-name {
    font-weight: bold;
    display: inline-flex;
  }

  & #profile-active {
    font-size: 13px;
    display: inline-flex;
  }
`;

export const LogOutButton = styled.button`
  border: none;
  width: 100%;
  border-top: 1px solid rgb(29, 28, 29);
  background: transparent;
  display: block;
  height: 33px;
  padding: 5px 20px 5px;
  outline: none;
  cursor: pointer;
`;

import styled from '@emotion/styled';

export const RegisterTeacherContainer = styled.div`
  width: 500px;
  margin-top: 20px;
  padding-top: 20px;
  padding-bottom: 10px;
  border-radius: 10px;
  margin-right: auto;
  margin-left: auto;
  border: 2px solid #0c5460;
`;
export const Form = styled.form`
  margin: 0 auto;
  width: 400px;
  max-width: 400px;
`;
export const DetailDiv = styled.div`
  margin: 0 auto;
  width: 400px;
  max-width: 400px;
`;
export const ImgContainer = styled.div`
  justify-content: center;
  align-items: center;
  display: flex;
  margin-bottom: 20px;
`;
export const ImgPreview = styled.img`
  width: 150px;
  height: 150px;
  border: none;
  border-radius: 50%;
`;
export const Label = styled.label`
  margin-bottom: 10px;
  width: 400px;
  & > span {
    display: block;
    text-align: left;
    padding-bottom: 8px;
    font-size: 15px;
    cursor: pointer;
    line-height: 1.46666667;
    font-weight: 700;
  }
`;

export const TextArea = styled.textarea`
  width: 400px;
  height: 150px;
  border: 2px solid var(--saf-0);
  border-radius: 4px;
  --saf-0: rgba(var(--sk_foreground_high_solid, 134, 134, 134), 1);
`;
export const Select = styled.select`
  border-radius: 4px;
  --saf-0: rgba(var(--sk_foreground_high_solid, 134, 134, 134), 1);
  border: 2px solid var(--saf-0);
  transition: border 80ms ease-out, box-shadow 80ms ease-out;
  box-sizing: border-box;
  margin: 0 0 10px;
  width: 100%;
  color: rgba(var(--sk_primary_foreground, 29, 28, 29), 1);
  background-color: rgba(var(--sk_primary_background, 255, 255, 255), 1);
  padding-left: 10px;
  padding-bottom: 2px;
  height: 44px;
  font-size: 18px;
  line-height: 1.33333333;
`;
export const Input = styled.input`
  border-radius: 4px;
  --saf-0: rgba(var(--sk_foreground_high_solid, 134, 134, 134), 1);
  border: 2px solid var(--saf-0);
  transition: border 80ms ease-out, box-shadow 80ms ease-out;
  box-sizing: border-box;
  margin: 0 0 10px;
  width: 100%;
  color: rgba(var(--sk_primary_foreground, 29, 28, 29), 1);
  background-color: rgba(var(--sk_primary_background, 255, 255, 255), 1);
  padding: 12px;
  height: 44px;
  padding-top: 11px;
  padding-bottom: 13px;
  font-size: 18px;
  line-height: 1.33333333;

  &:focus {
    --saf-0: rgba(var(--sk_highlight, 18, 100, 163), 1);
    box-shadow: 0 0 0 1px var(--saf-0), 0 0 0 5px rgba(29, 155, 209, 0.3);
  }
`;

export const Button = styled.button`
  color: #fff;
  background-color: #05495e;
  border: none;
  font-size: 18px;
  font-weight: 900;
  height: 44px;
  margin-bottom: 12px;
  width: 100%;
  max-width: 100%;
  min-width: 96px;
  padding: 0 16px 3px;
  transition: all 80ms linear;
  user-select: none;
  outline: none;
  cursor: pointer;
  border-radius: 4px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.3);

  &:hover {
    background-color: #1683a5ff;
    border: none;
  }

  &:focus {
    --saf-0: rgba(var(--sk_highlight, 18, 100, 163), 1);
    box-shadow: 0 0 0 1px var(--saf-0), 0 0 0 5px rgba(29, 155, 209, 0.3);
  }
`;

export const Error = styled.div`
  color: #e01e5a;
  margin: 0px 0 16px;
  // font-weight: bold;
`;

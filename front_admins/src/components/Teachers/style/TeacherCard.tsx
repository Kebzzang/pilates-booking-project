import styled from '@emotion/styled';

interface degree {
  degree: number;
}

export const GradientDiv = styled.div<degree>`
  height: 100px;
  background: ${(props) =>
    'linear-gradient(' +
    (props.degree * 90).toString() +
    'deg, rgba(5,73,94,1) 0%, rgba(36,128,157,1) 58%, rgba(62,209,255,1) 100%)'};
`;

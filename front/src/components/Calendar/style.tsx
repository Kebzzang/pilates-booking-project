import styled from '@emotion/styled';
import css from '@emotion/styled';
interface selected {
  day: string;
  selected: string;
}
interface dayStyle {
  dayStyle: string;
}
export const CalendarWrapper = styled.div`
  //text-align: center;
  margin-left: auto;
  margin-right: auto;
`;

export const YearMonth = styled.div`
  font-family: 'Segoe UI', serif;
  font-weight: bold;
  color: #05495e;
  font-size: 40px;
  height: 50px;
  text-align: center;
`;
export const HR = styled.hr`
  background-color: #05495e;
  margin-top: 0.3rem;
  margin-bottom: 0.8rem;
  height: 1px;
`;
export const DayPicker = styled.button<dayStyle>`
  width: 60px;
  font-family: 'Segoe UI', sans-serif;

  border-radius: 100%;
  background-color: ${(props) => props.dayStyle === 'selected' && '#05495e'};
  color: ${(props) => (props.dayStyle === 'selected' ? 'white' : '#282c34')};
  height: 60px;
  border-style: none;
  margin-bottom: 10px;
  margin-left: 3px;
  font-size: 17px;
  font-weight: bold;
  :disabled {
    background-color: white;
    color: darkgray;
  }
  &:hover:enabled {
    background-color: #1e5b6e;
    color: white;
    border-style: none;
  }
`;

export const DateInfo = styled.div`
  font-size: 10px;
  font-family: 'Segoe UI', serif;
  font-weight: lighter;
  margin-bottom: 0px;
  padding-bottom: 0px;
`;

export const CardHeaders = styled.div`
  margin-left: 10px;
  margin-top: 10px;
`;

export const NoClass = styled.div`
  color: #c80004;
  font-family: 'Segoe UI', serif;
  font-size: 20px;
  text-align: center;
`;

import styled from '@emotion/styled';
import css from '@emotion/styled';
interface selected {
  day: string;
  selected: string;
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

export const YoilButton = styled.button<selected>`
  width: 60px;
  font-family: 'Segoe UI', sans-serif;

  border-color: white;
  border-radius: 100%;
  background: ${(props) => (props.selected === props.day ? '#05495e' : 'white')};
  height: 60px;
  border-style: solid;
  margin-left: 3px;
  font-size: 17px;
  font-weight: bold;
  &:hover {
    background-color: #1e5b6e;
    color: white;
  }
  color: ${(props) => (props.selected === props.day ? 'white' : 'black')};
`;

export const SelectedYoilButton = styled(YoilButton)`
  background: #14a0a0;
  color: white;
`;

export const YoilButtonNalzza = styled.div`
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

export const MonthHeader = styled.h3();

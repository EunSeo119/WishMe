import React, { useState, useEffect } from 'react'
import style from './searchSchoolPage.module.css'
import axios from 'axios'
import { useNavigate } from "react-router-dom";

const SearchSchoolPage = () => {

    const [tempSchoolName, setTempSchoolName] = useState("");
    const [userSchoolUuid, setUserSchoolUuid] = useState(0);
    const [schoolList, setSchoolList] = useState([]);
    const [selectedIdx, setSelectedIdx] = useState(-1);
    const SERVER_URL = process.env.REACT_APP_SERVER_URL;

    const navigate = useNavigate();

    // 학교 
    const saveClick = () => {
        navigate(`/school/${userSchoolUuid}`);
    }

    // 학교 수정
    const changeSchool = (e) => {
        setTempSchoolName(e.target.value)
    }
    
    // 학교 선택
    const selectSchool = (schoolName, schoolUuid, idx) => {
        setTempSchoolName(schoolName)
        console.log(schoolUuid);
        setUserSchoolUuid(schoolUuid)
        // console.log(schoolName);
        setSelectedIdx(idx)
    }
    
    // 학교 리스트 가져오기
    const searchSchool = () => {
      axios({
        method: 'post',
        url: `${SERVER_URL}/api/users/search/school`,
        data: {
          schoolName: tempSchoolName
        }
      })
        .then((res) => {
          setSchoolList(res.data.data)
          console.log(res.data.data);
        })
        .catch((error) => {
          console.log('검색 중 오류 발생: ' + error)
        })
    }

    return (
        <div className={style.container}>
            <div className={style.school}>
                <div style={{width: '100%', fontSize: '35px', marginBottom: '50px'}}>학교 검색하기</div>
                <div style={{ display: 'flex', justifyContent: 'space-between', width: '100%' }}>
                    <div>학교 :</div>
                    <div>
                    <input type="text" value={tempSchoolName} onChange={(e) => changeSchool(e)} />
                    </div>
                    <div className={style.searchBtn} onClick={searchSchool}>
                    검색
                    </div>
                </div>
                {schoolList.length > 0 ? (
                    <>
                    <div className={style.schoolList}>
                        <ul>
                        {schoolList.map((school, idx) => (
                            <li key={school.schoolSeq} onClick={() => selectSchool(school.schoolName, school.uuid, idx)}
                            style={{ backgroundColor: selectedIdx === idx ? '#ececec' : 'white'}}> {school.schoolName}<br />
                            <div style={{ color: '#aeaeae' }}>{school.region}</div>
                            <hr />
                            </li>
                        ))}
                        </ul>
                    </div></>
                ) : (
                    <>
                    </>
                )}
            </div>
            <div className={style.completeBtn} onClick={saveClick}>다음</div>
        </div>
    );
};

export default SearchSchoolPage;
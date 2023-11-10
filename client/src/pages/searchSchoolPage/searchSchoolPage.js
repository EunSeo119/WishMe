import React, { useState, useEffect } from 'react'
import style from './searchSchoolPage.module.css'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
import { IoIosArrowBack } from 'react-icons/io'
import tokenHttp from '../../apis/tokenHttp'

const SearchSchoolPage = () => {
  const [tempSchoolName, setTempSchoolName] = useState('')
  const [userSchoolUuid, setUserSchoolUuid] = useState(0)
  const [schoolList, setSchoolList] = useState([])
  const [selectedIdx, setSelectedIdx] = useState(-1)
  const USER_SERVER = process.env.REACT_APP_USER_SERVER;

  const navigate = useNavigate()

  // 학교
  const saveClick = () => {
    if (userSchoolUuid !== 0) {
      navigate(`/school/${userSchoolUuid}`)
    } else {
      alert('학교가 선택되지 않았습니다!')
    }
  }

  //이전으로
  const onClickBeforeBtn = () => {
    navigate(-1)
  }

  // 엔터키로 동작
  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      searchSchool(e)
    }
  }

  // 마이페이지로 이동
  const goMypage = () => {
    const AccessToken = localStorage.getItem('AccessToken')
    if (AccessToken) {
      navigate(`/mypage`)
    } else {
      alert('로그인 후 이용가능합니다!')
    }
  }

  // 학교 수정
  const changeSchool = (e) => {
    setTempSchoolName(e.target.value)
  }

  // 학교 선택
  const selectSchool = (schoolName, schoolUuid, idx) => {
    setTempSchoolName(schoolName)
    setUserSchoolUuid(schoolUuid)
    setSelectedIdx(idx)
  }

  // 학교 리스트 가져오기
  const searchSchool = () => {
    axios({
      method: 'post',
      url: `${USER_SERVER}/api/users/search/school`,
      data: {
        schoolName: tempSchoolName
      }
    })
      .then((res) => {
        setSchoolList(res.data.data)
      })
      .catch((error) => {
      })
  }

  return (
    <div>
      <div className={style.beforeTitle} onClick={() => onClickBeforeBtn()}>
        <IoIosArrowBack className={style.icon} />
        이전으로
      </div>
      <div className={style.container}>
        <div className={style.school}>
          <div
            style={{ width: '100%', fontSize: '35px', marginBottom: '30px' }}
          >
            학교 검색하기
          </div>
          <div className={style.desc1}>
            {' '}
            * 학교를 선택하면 해당 학교에 온 응원을 볼 수 있어요!
          </div>
          <div className={style.desc2} onClick={goMypage}>
            {' '}
            마이페이지에서 내 학교 등록하기
          </div>
          <div
            style={{
              display: 'flex',
              justifyContent: 'space-between',
              width: '100%'
            }}
          >
            <div>학교 :</div>
            <div>
              <input
                type="text"
                value={tempSchoolName}
                onChange={(e) => changeSchool(e)}
                onKeyPress={handleKeyPress}
              />
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
                    <li
                      key={school.schoolSeq}
                      onClick={() =>
                        selectSchool(school.schoolName, school.uuid, idx)
                      }
                      style={{
                        backgroundColor:
                          selectedIdx === idx ? '#ececec' : 'white'
                      }}
                    >
                      {' '}
                      {school.schoolName}
                      <br />
                      <div style={{ color: '#aeaeae' }}>{school.region}</div>
                      <hr />
                    </li>
                  ))}
                </ul>
              </div>
            </>
          ) : (
            <></>
          )}
        </div>
        <div className={style.completeBtn} onClick={saveClick}>
          다음
        </div>
      </div>
    </div>
  )
}

export default SearchSchoolPage

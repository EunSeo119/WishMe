import React, { useState, useEffect } from 'react'
import style from './myPage.module.css'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
import { IoIosArrowBack } from 'react-icons/io'
import { set } from 'react-ga'

const MyPage = () => {
  const [deskName, setDeskName] = useState('')
  const [schoolName, setSchoolName] = useState('')
  const [isEditing, setIsEditing] = useState(false)
  const [tempDeskName, setTempDeskName] = useState(deskName)
  const [tempSchoolName, setTempSchoolName] = useState('')
  const [userSchoolSeq, setUserSchoolSeq] = useState(0)
  const SERVER_URL = process.env.REACT_APP_SERVER_URL
  const navigate = useNavigate() //변수 할당시켜서 사용

  //이전으로
  const onClickBeforeBtn = () => {
    navigate(-1)
  }

  // 값 수정
  const changeNickname = (e) => {
    setTempDeskName(e.target.value)
  }
  const changeSchool = (e) => {
    setTempSchoolName(e.target.value)
  }

  // 수정하기 버튼 활성화
  const editClick = () => {
    setIsEditing(true)
  }

  const saveClick = () => {
    const AccessToken = localStorage.getItem('AccessToken')
    const headers = {}

    if (AccessToken) {
      headers.Authorization = `Bearer ${AccessToken}`
    }

    setIsEditing(false)

    setDeskName(tempDeskName)

    const updatedData = {
      userNickname: tempDeskName,
      userSchoolSeq: userSchoolSeq
    }

    axios({
      method: 'put',
      url: `${SERVER_URL}/api/users/modify`,
      headers,
      data: updatedData
    })
      .then((response) => {
        // console.log('회원정보 수정: ', response)
        setDeskName(tempDeskName)
        setSchoolName(tempSchoolName)
      })
      .catch((error) => {
        console.error('API 요청 중 오류 발생:', error)
      })
  }

  // 엔터키로 동작
  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      searchSchool(e)
    }
  }

  // 학교 찾기
  const [schoolList, setSchoolList] = useState([])
  const [selectedSchool, setSelectedSchool] = useState('')
  const [selectedIdx, setSelectedIdx] = useState(-1)
  const [flag, setFlag] = useState(false)

  const AccessToken = localStorage.getItem('AccessToken')
  const headers = {}

  if (AccessToken) {
    headers.Authorization = `Bearer ${AccessToken}`
  }

  const searchSchool = () => {

    if(tempSchoolName == ''){
      alert("학교를 입력해주세요!");
    }else{
      axios({
        method: 'post',
        url: `${SERVER_URL}/api/users/search/school`,
        data: {
          schoolName: tempSchoolName
        }
      })
        .then((res) => {
            setFlag(true);
            setSchoolList(res.data.data);
        })
        .catch((error) => {
          setFlag(true);

          // 리스트 초기화
          setSchoolList([]);
          // console.log('검색 중 오류 발생: ' + error)
        })
    }
  }

  const selectSchool = (schoolName, schoolSeq, idx) => {
    setTempSchoolName(schoolName)
    setUserSchoolSeq(schoolSeq)
    // console.log(schoolName);
    setSelectedIdx(idx)
  }

  useEffect(() => {
    const AccessToken = localStorage.getItem('AccessToken')
    const headers = {}

    if (AccessToken) {
      headers.Authorization = `Bearer ${AccessToken}`
    }

    axios({
      method: 'get',
      url: `${SERVER_URL}/api/users`,
      headers
    })
      .then((response) => {
        const data = response.data
        // console.log(data);
        setDeskName(data.data.userNickname)
        setSchoolName(data.data.schoolName)
        setTempDeskName(data.data.userNickname)
        setTempSchoolName(data.data.schoolName)
      })
      .catch((error) => {
        console.error('API 요청 중 오류 발생:', error)
      })
  }, [])

  return (
    <div className={style.body}>
      <div className={style.beforeTitle} onClick={() => onClickBeforeBtn()}>
        <IoIosArrowBack className={style.icon} />
        이전으로
      </div>
      <div className={style.title}>
        <b>
          {deskName}님의
          <br />
          마이페이지
        </b>
      </div>
      <div className={style.form}>
        <div className={style.nickname}>
          <div>닉네임 :</div>
          {isEditing ? (
            <input type="text" value={tempDeskName} onChange={changeNickname} />
          ) : (
            <input
              type="text"
              value={deskName}
              style={{ color: 'gray', backgroundColor: '#edf4ef' }}
              disabled
              readOnly
            />
          )}
          <div></div>
        </div>
        <div className={style.school}>
          {/* <div>학교 :</div> */}
          {isEditing ? (
            <>
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
                    style={{width: '160px'}}
                    placeholder='학교 검색'
                    value={tempSchoolName}
                    onChange={(e) => changeSchool(e)}
                    onKeyPress={handleKeyPress}
                  />
                </div>
                <div className={style.searchBtn} onClick={searchSchool}>
                  검색
                </div>
              </div>
              {flag ? (
                <>
                  {schoolList.length > 0 ? (
                  <>
                    <div className={style.schoolList}>
                        <ul>
                          {schoolList.map((school, idx) => (
                            <li key={school.schoolSeq} onClick={() => selectSchool(school.schoolName, school.schoolSeq, idx)}
                              style={{backgroundColor: selectedIdx === idx ? '#ececec' : 'white'}}>
                              {school.schoolName}
                              <br />
                              <div style={{ color: '#aeaeae' }}>
                                {school.region}
                              </div>
                              <hr />
                            </li>
                          ))}
                        </ul>
                    </div>
                  </>
                ) : (
                  <>
                    <div className={style.schoolList} style={{overflowY: 'hidden'}}>
                      <div style={{width:'100%', height:'100%', textAlign:'center', marginTop:'80px'}}>검색 결과가 없습니다.</div>
                    </div>
                  </>
                )}
                </>
              ) : (
                <>
                </>
              )}
            </>
          ) : (
            <>
              <div
                style={{
                  display: 'flex',
                  justifyContent: 'space-between',
                  width: '100%'
                }}
              >
                <div>학교 :</div>
                <input
                  type="text"
                  value={schoolName}
                  style={{
                    color: 'gray',
                    backgroundColor: '#edf4ef',
                    width: '70%',
                    width: '210px'
                  }}
                  disabled
                  readOnly
                />
              </div>
            </>
          )}
        </div>
      </div>
      <div className={style.btn}>
        {isEditing ? (
          <div className={style.completeBtn} onClick={saveClick}>
            완료
          </div>
        ) : (
          <div className={style.modifyBtn} onClick={editClick}>
            수정하기
          </div>
        )}
      </div>
    </div>
  )
}

export default MyPage

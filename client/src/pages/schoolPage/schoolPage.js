import React, { useState, useEffect } from 'react'
import axios from 'axios'
import styleSchool from './schoolPage.module.css' // CSS 모듈을 import

const SchoolPage = () => {
  const [page, setPage] = useState(1)
  const [schoolName, setSchoolName] = useState('')
  const [totalCount, setTotalCount] = useState(0)
  const [schoolLetter, setSchoolLetter] = useState([])
  const [totalPage, setTotalPage] = useState(1)

  var url = 'http://localhost:8082/'

  useEffect(() => {
    axios
      //여기 학교변경해야함,,, 어케 해야하지?
      .get(`/api/school/letter/all/1/${page}`)
      .then((response) => {
        const data = response.data
        console.log(data)
        setSchoolName(data.schoolName)
        setTotalCount(data.totalCount)
        setSchoolLetter(data.schoolLetterList)
        setTotalPage(data.totalPage)
      })
      .catch((error) => {
        console.error('API 요청 중 오류 발생:', error)
      })
  }, [page])

  return (
    <div>
      <div className={styleSchool.main}>
        <img
          src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/background/schoolBackground.PNG"
          className={styleSchool.bg}
        />
        <div className={styleSchool.schoolName}>
          <b>{schoolName}</b>에
          <b>
            <br></br>
            {totalCount}
          </b>
          개의 응원이 왔어요!
        </div>

        <div className={styleSchool.gridContainer}>
          {schoolLetter.slice(0, 12).map((letter, index) => (
            <div key={index} className={styleSchool.gridItem}>
              <img src={`${letter.assetImg}`} />
            </div>
          ))}
        </div>
      </div>
      <div className={styleSchool.btn}>
        <div className={styleSchool.mySchoolBtn}>응원하기</div>
        <div className={styleSchool.mySchoolBtn}>내 책상 보기</div>
      </div>
    </div>
  )
}

export default SchoolPage

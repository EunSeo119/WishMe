import React, { useState, useRef, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios'
import style from './schoolLetterDetail.module.css'
import { useNavigate } from 'react-router-dom'
import { IoIosArrowBack, IoIosArrowForward, IoIosAlert } from 'react-icons/io'
import { PiSirenLight } from 'react-icons/pi'
import LetterReportModal from '../../Modal/letterReportModal'
const SchoolLetterDetail = () => {
  const { schoolUuid, letterId } = useParams()
  const [nickname, setNickname] = useState('')
  const [content, setContent] = useState('')
  const [schoolName, setSchoolName] = useState('')

  const navigate = useNavigate()

  // 신고하기 모달관련
  const [isModalOpen, setIsModalOpen] = useState(false)
  const openModal = () => {
    setIsModalOpen(true)
  }
  const closeModal = () => {
    setIsModalOpen(false)
  }

  useEffect(() => {
    axios
      .get(`https://wishme.co.kr/api/school/letter/one/${letterId}`)
      // .get(`http://localhost:8082/api/school/letter/one/${letterId}`)
      .then((response) => {
        const data = response.data
        console.log(data)
        setSchoolName(data.schoolLetterDetail.schoolName)
        setContent(data.schoolLetterDetail.content)
        setNickname(data.schoolLetterDetail.nickname)
      })
      .catch((error) => {
        // console.error('API 요청 중 오류 발생:', error)
      })
  }, [content])

  const goPre = () => {
    // navigate(`/school/${schoolUuid}`)
    navigate(-1)
  }

  const handleReportClick = () => {
    // navigate(`/school/${schoolUuid}`)
    //letterId 이게 편지 아이디요
    navigate(-1)
  }

  return (
    <div className={style.body}>
      <div className={style.schoolName} onClick={() => goPre()}>
        <IoIosArrowBack className={style.icon} />
        이전으로
      </div>
      <div className={style.title}>응원의 편지를 확인하세요!</div>
      <LetterReportModal
        isOpen={isModalOpen}
        onClose={closeModal}
        isSchool={true}
        letterId={letterId}
      />
      <div className={style.letterImgBack}>
        <img
          crossOrigin="anonymous"
          src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/letter/clovaLetter.png"
        />
        {/* 여기가 편지 내용 */}
        <div className={style.letter}>
          <div className={style.to}>
            <text className={style.letterPrefix}>To. {nickname}</text>
            <PiSirenLight
              className={style.reportLetterIcon}
              onClick={openModal}
            />
          </div>
          <div className={style.content}>
            <textarea
              className={style.contentTextarea}
              value={content}
              onChange={(e) => setContent(e.target.value)}
            />{' '}
          </div>
          <div className={style.from}>
            <text className={style.letterSurfix}>From. {schoolName}</text>
          </div>{' '}
        </div>
      </div>
      <div className={style.btn}>
        <div className={style.mySchoolBtn} onClick={() => goPre()}>
          닫기
        </div>
      </div>
    </div>
  )
}

export default SchoolLetterDetail

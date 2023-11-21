import React, { useState, useRef, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios'
import style from './deskLetterDetail.module.css'
import { useNavigate } from 'react-router-dom'
import { IoIosArrowBack, IoIosArrowForward, IoIosAlert } from 'react-icons/io'
import tokenHttp from '../../apis/tokenHttp'
import LetterReportModal from '../../Modal/letterReportModal'
import { PiSirenLight } from 'react-icons/pi'
import { PiDownloadSimple } from 'react-icons/pi'
import domtoimage from 'dom-to-image';
import { saveAs } from 'file-saver';

const DeskLetterDetail = () => {
  const { deskUuid, letterId, page } = useParams()
  const [nickname, setNickname] = useState('')
  const [toUserNickname, setToUserNickname] = useState('')
  const [content, setContent] = useState('')
  const [canReply, setCanReply] = useState(false)
  const [isMine, setIsMine] = useState(false)

  const MYLETTER_SERVER = process.env.REACT_APP_MYLETTER_SERVER

  const navigate = useNavigate()

  const cardRef = useRef();
  const onDownloadBtn = () => {
    const card = cardRef.current;
    const filter = (card) => {
      return card.id !== 'noDown';
    };
    domtoimage
      .toBlob(card, { filter: filter })
      .then((blob) => {
        saveAs(blob, 'wishme.png');
      });
  };

  // 신고하기 모달관련
  const [isModalOpen, setIsModalOpen] = useState(false)
  const openModal = () => {
    setIsModalOpen(true)
  }
  const closeModal = () => {
    setIsModalOpen(false)
  }

  useEffect(() => {
    const AccessToken = localStorage.getItem('AccessToken')
    const RefreshToken = localStorage.getItem('RefreshToken')

    if (AccessToken) {
      // AccessToken이 있으면 내 책상 페이지로 이동
      tokenHttp({
        method: 'get',
        url: `${MYLETTER_SERVER}/api/my/letter/detail/${letterId}`,
        headers: {
          Authorization: `Bearer ${AccessToken}`,
          RefreshToken: `${RefreshToken}`
        }
      })
        .then((response) => {
          const data = response.data
          setContent(data.content)
          setNickname(data.fromUserNickname)
          setCanReply(data.canReply)
          setIsMine(data.isMine)
          setToUserNickname(data.toUserNickname)
        })
        .catch((error) => {
        })
    } else {
      axios({
        method: 'get',
        url: `${MYLETTER_SERVER}/api/my/letter/detail/${letterId}`
      })
        .then((response) => {
          const data = response.data
          setContent(data.content)
          setNickname(data.fromUserNickname)
          setCanReply(data.canReply)
          setToUserNickname(data.toUserNickname)
        })
        .catch((error) => {
        })
    }
  }, [content])

  const goPre = () => {
    navigate(`/desk/${deskUuid}/${page}`)
  }

  const writeReplyLetter = () => {
    navigate(`/replyWritePage/${letterId}`)
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
        isSchool={false}
        letterId={letterId}
      />
      <div className={`${style.letterImgBack}`}>
        <div className='card' ref={cardRef} >
          <img
            crossOrigin="anonymous"
            src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/letter/clovaLetter.png"
          />
          {/* 여기가 편지 내용 */}
          <div className={style.letter}>
            <div className={style.to}>
              <text className={style.letterPrefix}>To. {toUserNickname}</text>
              <PiSirenLight
                className={style.reportLetterIcon}
                id='noDown'
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
              {isMine ? (
                <>
                  <PiDownloadSimple
                    className={style.downLoadIcon}
                    id='noDown'
                    onClick={onDownloadBtn}
                  />
                </>
              ) : (<></>)}
              <text className={style.letterSurfix}>From. {nickname}</text>
            </div>{' '}
          </div>
        </div>
      </div>
      <div className={style.btn}>
        {canReply ? (
          <>
            <div style={{ display: 'flex', justifyContent: 'space-around' }}>
              <div className={style.replyBtn} onClick={() => writeReplyLetter()}>답장하기</div>
              <div className={style.closeBtn} onClick={() => goPre()}>닫기</div>
            </div>
          </>
        ) : (
          <>
            <div className={style.mySchoolBtn} onClick={() => goPre()}>
              닫기
            </div>
          </>
        )}
      </div>
    </div>
  )
}

export default DeskLetterDetail

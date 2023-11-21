import React from "react";
import { Helmet } from "react-helmet";

const Clarity = () => {
    const CLARITY_ID = process.env.REACT_APP_CLARITY_ID;
    return (
        <div>
            <Helmet>
                <script type="text/javascript">
                    {`
                    (function(c,l,a,r,i,t,y){
                        c[a]=c[a]||function(){(c[a].q=c[a].q||[]).push(arguments)};
                        t=l.createElement(r);t.async=1;t.src="https://www.clarity.ms/tag/"+i;
                        y=l.getElementsByTagName(r)[0];y.parentNode.insertBefore(t,y);
                    })(window, document, "clarity", "script", "${CLARITY_ID}");
            `}
                </script>
            </Helmet>
            {/* 나머지 컴포넌트 내용 */}
        </div>
    );
};

export default Clarity;

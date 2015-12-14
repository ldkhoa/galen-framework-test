@objects

	gooddata-logo		css	.logoArea-big>img

    username-textfield  css input[name='email']
    password-textfield  css input[name='password']

    signIn-button       css .s-btn-sign_in


= Login page =
    @on *
        username-textfield, password-textfield:
            height 33px

        signIn-button:
            height 38px

        username-textfield:
            aligned vertically all password-textfield

        password-textfield:
            aligned vertically left signIn-button

        signIn-button:
            text is "Sign in"

    @on desktop
    	gooddata-logo:
			height 116px
			width 115px
			
        username-textfield, password-textfield:
            right-of gooddata-logo 200 to 284px

        signIn-button:
            near gooddata-logo 200 to 284px right, 100px bottom

    @on mobile
    	gooddata-logo:
			height 70px
			width 69px

        username-textfield:
            aligned vertically all gooddata-logo
            below gooddata-logo 120 to 131px

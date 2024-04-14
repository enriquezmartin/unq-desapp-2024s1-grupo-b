package ar.unq.desapp.grupob.backenddesappapi.model

import ar.unq.desapp.grupob.backenddesappapi.helpers.UserBuilder
import ar.unq.desapp.grupob.backenddesappapi.utlis.UserCannotBeRegisteredException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class CryptoExchangeSystemTest {

    val system: CryptoExchangeSystem = CryptoExchangeSystem()

    @Test
    fun `when an user with invalid name is registered an exception is raised`(){
        val anUserWithNullName: UserEntity = UserBuilder().build()
        val anUserWithShortName: UserEntity = UserBuilder()
            .withName("as")
            .build()
        val anUserWithLongName: UserEntity = UserBuilder()
            .withName("My name is too long to be registered in ths application")
            .build()
        val expectedMessageForNullName = "The name cannot be null"
        val expectedMessageForInvalidName = "The name is too short or too long"

        val exceptionMessageForNullName: String = assertThrows<UserCannotBeRegisteredException> { system.register(anUserWithNullName) }.message
        val exceptionMessageForShortName: String = assertThrows<UserCannotBeRegisteredException> { system.register(anUserWithShortName) }.message
        val exceptionMessageForLongName: String = assertThrows<UserCannotBeRegisteredException> { system.register(anUserWithLongName) }.message

        assertEquals(exceptionMessageForNullName, expectedMessageForNullName)
        assertEquals(exceptionMessageForShortName, expectedMessageForInvalidName)
        assertEquals(exceptionMessageForLongName, expectedMessageForInvalidName)
    }

    @Test
    fun `when an user with invalid surname is registered an exception is raised`(){
        val anUserWithShortSurname: UserEntity = UserBuilder()
            .withName("a valid name")
            .withSurname("as")
            .build()
        val anUserWithLongSurname: UserEntity = UserBuilder()
            .withName("a valid name")
            .withSurname("My surname is too long to be registered in ths application")
            .build()
        val anUserWithNullSurname: UserEntity = UserBuilder()
            .withName("a valid name")
            .build()
        val expectedMessageForNullSurame = "The surname cannot be null"
        val expectedMessageForInvalidSurame = "The surname is too short or too long"

        val exceptionMessageForNullSurname: String = assertThrows<UserCannotBeRegisteredException> { system.register(anUserWithNullSurname) }.message
        val exceptionMessageForShortSurname: String = assertThrows<UserCannotBeRegisteredException> { system.register(anUserWithShortSurname) }.message
        val exceptionMessageForLongSurname: String = assertThrows<UserCannotBeRegisteredException> { system.register(anUserWithLongSurname) }.message

        assertEquals(exceptionMessageForNullSurname, expectedMessageForNullSurame)
        assertEquals(exceptionMessageForShortSurname, expectedMessageForInvalidSurame)
        assertEquals(exceptionMessageForLongSurname, expectedMessageForInvalidSurame)
    }

    @Test
    fun `when an user with invalid email is registered an exception is raised`(){
        val anUserWithInvalidEmail: UserEntity = UserBuilder()
            .withName("a valid name")
            .withSurname("a valid username")
            .withEmail("an invalid emal")
            .build()
        val anUserWithNullEmail: UserEntity = UserBuilder()
            .withName("a valid name")
            .withSurname("a valid username")
            .build()
        val expectedMessageForInvalidFormat = "The email does not have a valid format"
        val expectedMessageForNullEmail = "The email cannot be null"

        val exceptionMessageForInvalidEmail: String = assertThrows<UserCannotBeRegisteredException> { system.register(anUserWithInvalidEmail) }.message
        val exceptionMessageForNullEmail: String = assertThrows<UserCannotBeRegisteredException> { system.register(anUserWithNullEmail) }.message

        assertEquals(exceptionMessageForInvalidEmail, expectedMessageForInvalidFormat)
        assertEquals(exceptionMessageForNullEmail, expectedMessageForNullEmail)
    }

    @Test
    fun `when an user with invalid address is registered an exception is raised`(){
        val anUserWithNullAddress: UserEntity = UserBuilder()
            .withName("a valid name")
            .withSurname("a valid username")
            .withEmail("valid@email.com")
            .build()
        val anUserWithShortAddress: UserEntity = UserBuilder()
            .withName("a valid name")
            .withSurname("a valid username")
            .withEmail("valid@email.com")
            .withAddress("invalid")
            .build()
        val anUserWithLongtAddress: UserEntity = UserBuilder()
            .withName("a valid name")
            .withSurname("a valid username")
            .withEmail("valid@email.com")
            .withAddress("an address too large to be registered in this application")
            .build()

        val expectedMessageForNullAddress = "The address cannot be null"
        val expectedMessageForShortAddress = "The address is too short or too long"
        val expectedMessageForLongAddress = "The address is too short or too long"

        val exceptionMessageForNullAddress: String = assertThrows<UserCannotBeRegisteredException> { system.register(anUserWithNullAddress) }.message
        val exceptionMessageForShortAddress: String = assertThrows<UserCannotBeRegisteredException> { system.register(anUserWithShortAddress) }.message
        val exceptionMessageForLongAddress: String = assertThrows<UserCannotBeRegisteredException> { system.register(anUserWithLongtAddress) }.message

        assertEquals(exceptionMessageForNullAddress, expectedMessageForNullAddress)
        assertEquals(exceptionMessageForShortAddress, expectedMessageForShortAddress)
        assertEquals(exceptionMessageForLongAddress, expectedMessageForLongAddress)
    }

    @Test
    fun `when an user with invalid or null password is registered, an exception is raised`(){
        val anUserWithNullPassword: UserEntity = UserBuilder()
            .withName("a valid name")
            .withSurname("a valid username")
            .withEmail("valid@email.com")
            .withAddress("a valid address")
            .build()
        val anUserWithInvalidPassword: UserEntity = UserBuilder()
            .withName("a valid name")
            .withSurname("a valid username")
            .withEmail("valid@email.com")
            .withAddress("a valid address")
            .withPassword("a password")
            .build()
        val expectedMessageNullPassword = "The password cannot be null"
        val expectedMessageWeakPassword = "The password is too weak"

        val exceptionMessageForNullPassword: String = assertThrows<UserCannotBeRegisteredException> { system.register(anUserWithNullPassword) }.message
        val exceptionMessageForWeakPassword: String = assertThrows<UserCannotBeRegisteredException> { system.register(anUserWithInvalidPassword) }.message

        assertEquals(exceptionMessageForNullPassword, expectedMessageNullPassword)
        assertEquals(exceptionMessageForWeakPassword, expectedMessageWeakPassword)
    }

    @Test
    fun `when an user with invalid or null cvu is registered, an exception is raised`(){
        val anUserWithNullCVU: UserEntity = UserBuilder()
            .withName("a valid name")
            .withSurname("a valid username")
            .withEmail("valid@email.com")
            .withAddress("a valid address")
            .withPassword("a Se_cure password")
            .build()
        val anUserWithInvalidCVU: UserEntity = UserBuilder()
            .withName("a valid name")
            .withSurname("a valid username")
            .withEmail("valid@email.com")
            .withAddress("a valid address")
            .withPassword("a Se_cure password")
            .withCvuMP("1111")
            .build()
        val expectedMessageForNullCVU = "The cvu cannot be null"
        val expectedMessageForInvalidCVU = "The cvu must have 22 digits"

        val exceptionMessageForNullCVU: String = assertThrows<UserCannotBeRegisteredException> { system.register(anUserWithNullCVU) }.message
        val exceptionMessageForInvalidCVU: String = assertThrows<UserCannotBeRegisteredException> { system.register(anUserWithInvalidCVU) }.message

        assertEquals(exceptionMessageForNullCVU, expectedMessageForNullCVU)
        assertEquals(exceptionMessageForInvalidCVU, expectedMessageForInvalidCVU)
    }

    @Test
    fun `when an user with invalid or null walletAddress is registered, an exception is raised`(){
        val anUserWithNullWalletAddress: UserEntity = UserBuilder()
            .withName("a valid name")
            .withSurname("a valid username")
            .withEmail("valid@email.com")
            .withAddress("a valid address")
            .withPassword("a Se_cure password")
            .withCvuMP("1234567890123456789012")
            .build()
        val anUserWithInvalidWalletAddress: UserEntity = UserBuilder()
            .withName("a valid name")
            .withSurname("a valid username")
            .withEmail("valid@email.com")
            .withAddress("a valid address")
            .withPassword("a Se_cure password")
            .withCvuMP("1234567890123456789012")
            .withWalletAddress("123")
            .build()
        val expectedMessageForNullWalletAddress = "The walletAddress cannot be null"
        val expectedMessageForInvalidWalletAddress = "The walletAddress must have 8 digits"

        val exceptionMessageForNullCVU: String = assertThrows<UserCannotBeRegisteredException> { system.register(anUserWithNullWalletAddress) }.message
        val exceptionMessageForInvalidCVU: String = assertThrows<UserCannotBeRegisteredException> { system.register(anUserWithInvalidWalletAddress) }.message

        assertEquals(exceptionMessageForNullCVU, expectedMessageForNullWalletAddress)
        assertEquals(exceptionMessageForInvalidCVU, expectedMessageForInvalidWalletAddress)
    }



//    @Test
//    fun assd(){
//        val user: UserEntity = UserBuilder()
//            .withName("a name")
//            .withSurname("a surname")
//            .withEmail("an email")
//            .withAddress("an address")
//            .withPassword("a Se_cure password")
//            .withCvuMP("1111111111111111111111")
//            .withWalletAddress("12345678")
//            .build()
//        system.registrar(user)
//
//    }
}
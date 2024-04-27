package ar.unq.desapp.grupob.backenddesappapi.model

import ar.unq.desapp.grupob.backenddesappapi.dto.IntentItem
import ar.unq.desapp.grupob.backenddesappapi.helpers.UserBuilder
import ar.unq.desapp.grupob.backenddesappapi.utlis.UserCannotBeRegisteredException
import ar.unq.desapp.grupob.backenddesappapi.utlis.UserNotRegisteredException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class CryptoExchangeSystemTest {

    @DisplayName("REGISTER FEATURE")
    @Nested
    inner class RegisterFeature(){
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

        @Test
        fun `when a user with all valid attributes is registered, this exists as a registered user in the system`(){
            val aValidUser: UserEntity = UserBuilder()
                .withId(1L)
                .withName("a valid name")
                .withSurname("a valid username")
                .withEmail("valid@email.com")
                .withAddress("a valid address")
                .withPassword("a Se_cure password")
                .withCvuMP("1234567890123456789012")
                .withWalletAddress("12345678")
                .build()
            val existsUserWithId: Boolean = system.getUserById(1L) != null

            system.register(aValidUser)
            val registeredUser: UserEntity? = system.getUserById(1L)

            assertFalse(existsUserWithId)
            assertEquals(aValidUser.id, registeredUser!!.id)
            assertEquals(aValidUser.name, registeredUser!!.name)
            assertEquals(aValidUser.surname, registeredUser!!.surname)
            assertEquals(aValidUser.email, registeredUser!!.email)
            assertEquals(aValidUser.address, registeredUser!!.address)
            assertEquals(aValidUser.password, registeredUser!!.password)
            assertEquals(aValidUser.cvu, registeredUser!!.cvu)
            assertEquals(aValidUser.walletAddress, registeredUser!!.walletAddress)
        }
    }

    @DisplayName("PRICES FEATURE")
    @Nested
    inner class PricesFeature(){

        private val system: CryptoExchangeSystem = CryptoExchangeSystem()
        @Test
        fun `When no price is registered for a given cryptocurrency, there is no price for it`(){
            val prices: MutableList<Price> = system.getPricesFor(CryptoCurrency.ALICEUSDT)

            assertTrue(prices.isEmpty())
        }

        @Test
        fun `When a price is registered for a given crypto currency, the price is the registered price`(){
            val price: Price = Price(CryptoCurrency.ALICEUSDT, LocalDate.now(), 100.0F)

            system.registerPrice(CryptoCurrency.ALICEUSDT, price)
            val prices: List<Price> = system.getPricesFor(CryptoCurrency.ALICEUSDT)
            val registeredPrice: Price = prices[0]

            assertTrue(prices.isNotEmpty())
            assertEquals(registeredPrice.value, price.value)
            assertEquals(registeredPrice.priceTime, price.priceTime)
            assertEquals(registeredPrice.criptoCurrency, price.criptoCurrency)
        }

        @Test
        fun `When two prices are registered for a given crypto currency, only the ones in the range of the latest 24 hs are retrieved`(){
            val priceForToday: Price = Price(CryptoCurrency.ALICEUSDT, LocalDate.now(), 100.0F)
            val priceForTheDayBeforeYesterday: Price = Price(CryptoCurrency.ALICEUSDT, LocalDate.now().minusDays(2), 200.0F)
            system.registerPrice(CryptoCurrency.ALICEUSDT, priceForToday)
            system.registerPrice(CryptoCurrency.ALICEUSDT, priceForTheDayBeforeYesterday)

            val pricesForToday: List<Price> = system.getPricesForTheLast24hs(CryptoCurrency.ALICEUSDT)

            assertTrue(pricesForToday.size == 1)
            assertEquals(pricesForToday[0].value, 100.0F)
        }
    }

    @DisplayName("PURCHASE/SALE INTENT")
    @Nested
    inner class PurchaseSaleIntent{

        private val system: CryptoExchangeSystem = CryptoExchangeSystem()
        private val user:UserEntity = UserBuilder()
            .withName("a valid name")
            .withId(1L)
            .withSurname("a valid surname")
            .withEmail("valid@email.com")
            .withWalletAddress("12345678")
            .withCvuMP("1234567890123456789012")
            .withAddress("a valid address")
            .withPassword("a_Password")
            .build()
        private val interestedUser:UserEntity = UserBuilder()
            .withName("a valid name")
            .withId(2L)
            .withSurname("a valid surname")
            .withEmail("valid@email.com")
            .withWalletAddress("12345678")
            .withCvuMP("1234567890123456789012")
            .withAddress("a valid address")
            .withPassword("a_Password")
            .build()

        @BeforeEach
        fun setup(){
            system.register(user)
        }

        @Test
        fun `A non existent user cannot post`(){
            val expectedMessage: String = "The user does not exist"

            val exceptionMessageForNonExistentUser: String = assertThrows<UserNotRegisteredException> { system.getPostByUser(
                2L,
            )}.message

            assertEquals(expectedMessage, exceptionMessageForNonExistentUser)
        }

        @Test
        fun `New user has zero posts`(){
            val result: MutableSet<Post> = system.getPostByUser(1L)

            assertTrue(result.isEmpty())
        }

        @Test
        fun `User successfully posts an intent`(){
            val post: Post = Post(1L, CryptoCurrency.ALICEUSDT, 100.0F, 20.0F, OperationType.PURCHASE)
            system.addPost(post, user.id!!)
            val posts: MutableSet<Post> = system.getPostByUser(1L)

            assertTrue(posts.isNotEmpty())
            assertEquals(posts.first().user!!.id, 1L)
            assertEquals(posts.first().createdDate, LocalDate.now())
            assertEquals(posts.first().priceInArs, post.priceInArs)
            assertEquals(posts.first().cryptoCurrency, post.cryptoCurrency)
            assertEquals(posts.first().amount, post.amount)
            assertEquals(posts.first().price, post.price)
            assertEquals(posts.first().operationType, post.operationType)
        }

        @Test
        fun `When a user registers two intents, one for purchase and one for sale, and one type is requested, these are the ones retrieved`(){
            val purchaseIntentPost: Post = Post(1L, CryptoCurrency.ALICEUSDT, 100.0F, 20.0F, OperationType.PURCHASE)
            val saleIntentPost: Post = Post(2L, CryptoCurrency.ALICEUSDT, 100.0F, 20.0F, OperationType.SALE)
            system.addPost(purchaseIntentPost, 1L)
            system.addPost(saleIntentPost, 1L)

            val activePurchaseIntents: List<IntentItem> = system.activePostByUserAndType(1L, OperationType.PURCHASE)

            assertTrue(activePurchaseIntents.size == 1)
            assertEquals(activePurchaseIntents[0].operationType, OperationType.PURCHASE)
            assertEquals(activePurchaseIntents[0].user, "${user.name} ${user.surname}")
            assertEquals(activePurchaseIntents[0].operations, 0)
            assertEquals(activePurchaseIntents[0].reputation, "No operations")
        }

        @Test
        fun `when a purchase is `(){
            val purchaseIntentPost: Post = Post(1L, CryptoCurrency.ALICEUSDT, 100.0F, 20.0F, OperationType.SALE)
            system.addPost(purchaseIntentPost, 1L)

            system.wireTransferNotice(purchaseIntentPost.id!!, interestedUser.id)
        }
    }
}